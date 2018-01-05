### 参数设置

Camera.Parameters param = camera.getParameters();
param.setPreviewSize(width, height);

width一般大于height，默认摄像头是横向，预览时需要设置camera.setDisplayOrientation(90);

### 预览大小
常见的分辨率为4:3，或者16:9，即640x480和1280x720

Android6.0的源代码中，预览的大小和PictureSize有关，先看看系统如何设置Picture Size

#### PictureSize
[代码位置](https://github.com/stdying/android-6.0.1_r1/blob/master/packages/apps/Camera/src/com/android/camera/CameraSettings.java)

```java
 public static void initialCameraPictureSize(
            Context context, Parameters parameters) {
        // When launching the camera app first time, we will set the picture
        // size to the first one in the list defined in "arrays.xml" and is also
        // supported by the driver.
        List<Size> supported = parameters.getSupportedPictureSizes();
        if (supported == null) return;
        for (String candidate : context.getResources().getStringArray(
                R.array.pref_camera_picturesize_entryvalues)) {
            if (setCameraPictureSize(candidate, supported, parameters)) {
                SharedPreferences.Editor editor = ComboPreferences
                        .get(context).edit();
                editor.putString(KEY_PICTURE_SIZE, candidate);
                editor.apply();
                return;
            }
        }
        Log.e(TAG, "No supported picture size found");
    }
```
读取预先pref_camera_picturesize_entryvalues配置大小，然后在和设备支持的尺寸匹配，两个相同的作为pictureSize；并保存。

R.array.pref_camera_picturesize_entryvalues 中值为
```xml
<!-- When launching the camera app first time, we will set the picture
         size to the first one in the list that is also supported by the
         driver -->
    <string-array name="pref_camera_picturesize_entryvalues" translatable="false">
        <item>3264x2448</item>
        <item>2592x1944</item>
        <item>2592x1936</item>
        <item>2560x1920</item>
        <item>2048x1536</item>
        <item>1600x1200</item>
        <item>1280x960</item>
        <item>1024x768</item>
        <item>640x480</item>
        <item>320x240</item>
    </string-array>
```
setCameraPictureSize 方法比较预先设置的之中是否有设置支持的大小相等的值
```java
public static boolean setCameraPictureSize(
            String candidate, List<Size> supported, Parameters parameters) {
        int index = candidate.indexOf('x');
        if (index == NOT_FOUND) return false;
        int width = Integer.parseInt(candidate.substring(0, index));
        int height = Integer.parseInt(candidate.substring(index + 1));
        for (Size size : supported) {
            if (size.width == width && size.height == height) {
                parameters.setPictureSize(width, height);
                return true;
            }
        }
        return false;
    }
```

#### 预览大小设置

- 系统Camera设置

系统相机预览大小的设置和picturesize有关。

在startPreview中会调用updateCameraParametersPreference()方法

```java
// Set the preview frame aspect ratio according to the picture size.
        Size size = mParameters.getPictureSize();

        mPreviewPanel = findViewById(R.id.frame_layout);
        mPreviewFrameLayout = (PreviewFrameLayout) findViewById(R.id.frame);
        mPreviewFrameLayout.setAspectRatio((double) size.width / size.height);

        // Set a preview size that is closest to the viewfinder height and has
        // the right aspect ratio.
        List<Size> sizes = mParameters.getSupportedPreviewSizes();
        Size optimalSize = Util.getOptimalPreviewSize(this,
                sizes, (double) size.width / size.height);
        Size original = mParameters.getPreviewSize();
        if (!original.equals(optimalSize)) {
            mParameters.setPreviewSize(optimalSize.width, optimalSize.height);

            // Zoom related settings will be changed for different preview
            // sizes, so set and read the parameters to get lastest values
            mCameraDevice.setParameters(mParameters);
            mParameters = mCameraDevice.getParameters();
        }
```
调用Util.getOptimalPreviewSize()方法获取合适previewSize，

```java
    public static Size getOptimalPreviewSize(Activity currentActivity,
            List<Size> sizes, double targetRatio) {
        // Use a very small tolerance because we want an exact match.
        final double ASPECT_TOLERANCE = 0.001;
        if (sizes == null) return null;

        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        // Because of bugs of overlay and layout, we sometimes will try to
        // layout the viewfinder in the portrait orientation and thus get the
        // wrong size of preview surface. When we change the preview size, the
        // new overlay will be created before the old one closed, which causes
        // an exception. For now, just get the screen size.
        //获取屏幕大小
        Point point = getDefaultDisplaySize(currentActivity, new Point());
        //相机默认横屏，高度是两者中最小的一个
        int targetHeight = Math.min(point.x, point.y);
        // Try to find an size match aspect ratio and size
        for (Size size : sizes) {
            double ratio = (double) size.width / size.height;
            //相机支持的比率和targetRatio相差太大时，继续查找
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            //比率满足时，高度不能差太多
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }
        // Cannot find the one match the aspect ratio. This should not happen.
        // Ignore the requirement.
        if (optimalSize == null) {
            Log.w(TAG, "No preview size match the aspect ratio");
            minDiff = Double.MAX_VALUE;
            for (Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
```
根据targetRatio，和实际屏幕大小来选择SupportedPreviewSizes中合适的值。targetRatio我觉得也可以使用屏幕比例。

- Zing中previewSize设置

[代码位置](https://android.googlesource.com/platform/external/zxing/+/android-6.0.1_r81/qr_scanner/src/com/google/zxing/client/android/camera/CameraConfigurationManager.java)

initFromCameraParameters()方法会调用findBestPreviewSizeValue()方法获取适合的大小；screenResolution为屏幕大小

```java
  //private static final int MIN_PREVIEW_PIXELS = 320 * 240; // small screen
  //private static final int MAX_PREVIEW_PIXELS = 800 * 480; // large/HD screen
  private static Point findBestPreviewSizeValue(Camera.Parameters parameters,
                                                Point screenResolution,
                                                boolean portrait) {
    Point bestSize = null;
    int diff = Integer.MAX_VALUE;
    for (Camera.Size supportedPreviewSize : parameters.getSupportedPreviewSizes()) {
      int pixels = supportedPreviewSize.height * supportedPreviewSize.width;
      //预先设置大小
      if (pixels < MIN_PREVIEW_PIXELS || pixels > MAX_PREVIEW_PIXELS) {
        continue;
      }
      int supportedWidth = portrait ? supportedPreviewSize.height : supportedPreviewSize.width;
      int supportedHeight = portrait ? supportedPreviewSize.width : supportedPreviewSize.height;
      //不太理解为啥要交叉相乘，总之是比较差值
      int newDiff = Math.abs(screenResolution.x * supportedHeight - supportedWidth * screenResolution.y);
      if (newDiff == 0) {
        bestSize = new Point(supportedWidth, supportedHeight);
        break;
      }
      //更新最小差值
      if (newDiff < diff) {
        bestSize = new Point(supportedWidth, supportedHeight);
        diff = newDiff;
      }
    }
    //如果还没找到，就使用预览值
    if (bestSize == null) {
      Camera.Size defaultSize = parameters.getPreviewSize();
      bestSize = new Point(defaultSize.width, defaultSize.height);
    }
    return bestSize;
  }

```
zxing中是根据实际屏幕值，根设备支持的尺寸交叉相乘，获取差值最小设为最合适的值，如果没有使用预览值。

> zxing使用方法比较简单些。