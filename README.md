# android-viewex

This library contains some implementations general purpose Views for Android Application.


## CircleProgressView

"Progress Ring" with a percent label.
This implementation comes from [toyota-m2k/CircularProgressBar](https://github.com/toyota-m2k/CircularProgressBar). The implementaton for iOS is in it, and for Windows (WPF) is in [toyota-m2k/wpf-little-toolkit](https://github.com/toyota-m2k/wpf-little-toolkit).

## FrameLayoutEx

Subclass of FrameLayout which support maxWidth/maxHeight properties.
I don't know why the original FrameLayout (or ViewGroup) is not support those properties, it is too inconvenient.
A little trick in measuring size of view makes it possible to limit the width/height of FrameLayout.
Though it might be applicable for other ViewGroups (LinearLayout, GridLayout, ConstraintLayout ...), I think we will have same effect by ViewGroup with MATCH_PARENT as child of the limitted-sized FrameLayout.

## Viewbox

A view similar to Viewbox in WPF, can stretch and scale a single child to fill it's size.
