# VMathQuill

VMathQuill is a [Vaadin](http://www.vaadin.com) integration of the [MathQuill](https://github.com/laughinghan/mathquill) javascript library. It offers familiar Vaadin concepts such as Labels and TextFields as MathQuill enabled variants. This integration also uses [CodeCogs](http://www.codecogs.com/latex/htmlequations.php) to render math in some parts. The demo can be found [here](http://risto.virtuallypreinstalled.com/mathquill/).

Currently VMathQuill includes the following components.

## MathLabel

Statically displays MathQuill rendered math.

## MathTextField

Allows editing math via a MathQuill editable or textbox. The user can add latex commands from the server side to the current cursor position. The component also tries to use the selection if there is one, e.g. if *\sqrt{}* is added and the user has selected *x^2*, the result will be *\sqrt{x^2}*.

## RichMathArea

Normal Vaadin RichTextArea, but with an added button for adding math. MathQuill is used to render the input math, but due to technical limitations the actual math in the editable area is rendered as an image. Clicking the image displays a MathQuill rendered editor.

## GlobalToolbar

As seen on the demo, toolbars for a single MathTextField can be built from normal Vaadin components. GlobalToolbar is intended for building a floating toolbar that targets any math field that has had focus, even those by RichMathArea.