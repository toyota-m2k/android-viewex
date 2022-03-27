# android-viewex

This library contains some implementations of general purpose Views for Android application.


## CircleProgressView

"Progress Ring" with a percent label.
This implementation comes from [toyota-m2k/CircularProgressBar](https://github.com/toyota-m2k/CircularProgressBar). The implementaton for iOS is in it, and for Windows (WPF: Windows Presentation Foundation) is in [toyota-m2k/wpf-little-toolkit](https://github.com/toyota-m2k/wpf-little-toolkit).

## FrameLayoutEx

Subclass of FrameLayout which support `maxWidth` / `maxHeight` properties.
I don't know why the original FrameLayout (or ViewGroup) is not support those properties, it is too inconvenient.
A little trick in measuring size of view makes it possible to limit the width/height of FrameLayout.
Though it might be applicable for other ViewGroups (LinearLayout, GridLayout, ConstraintLayout ...), I think we will have same effect by the ViewGroup with MATCH_PARENT as a child of the size-limitted FrameLayout.

## Viewbox

This is a view similar to Viewbox in WPF and it can stretch and scale a single child to fill it's size.
Viewbox is inherited from `FrameLayoutEx`, so it supports `maxWidth` / `maxHeight` properties.
And it also supports `expandable` property (default is false). Setting `expandable = true` allows Viewbox to reduce or expand the size of the child to fit, otherwise allows only to reduce the size of the child and renders it according in its gravity.

# Gradle

Add it in your root build.gradle at the end of repositories:
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
Add the dependency
```
dependencies {
  implementation 'com.github.toyota-m2k:android-viewex:Tag'
}
```
