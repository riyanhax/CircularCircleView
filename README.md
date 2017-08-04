# CircularCircleView
a circular circle view.
## snapshot
![smaple gif](https://github.com/timshinlee/CircularCircleView/blob/master/app/snapshot/loading.gif)
## import
build.gradle of <b>project</b>
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
build.gradle of <b>module</b>
```
dependencies {
        compile 'com.github.timshinlee:CircularCircleView:1.0.0'
}
```
## attributes
1. duration(optional):     duration of two rounds
2. radius(optional):       radius of the circle
3. strokeWidth(optional):  stroke of the circle
4. colors(optional):       the colors to display

You may define color in the res/values/colors.xml as follows:
```
<array name="my_circular_colors">
    <item>@android:color/holo_orange_light</item>
    <item>@android:color/holo_green_light</item>
    <item>#FF4081</item>
</array>
```
sample usage:
```
<com.timshinlee.circularcircleview.CircularCircleView
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/circle"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center"
    app:colors="@array/my_circular_colors"
    app:duration="2000"
    app:radius="60dp"
    app:strokeWidth="5dp"/>
```
