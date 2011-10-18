package org.vaadin.risto.mathquill.client.ui.external;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

/**
 * This {@link ClientBundle} is used for all the button icons. Using a bundle
 * allows all of these images to be packed into a single image, which saves a
 * lot of HTTP requests, drastically improving startup time.
 */
// copied due private variables
public interface Images extends ClientBundle {

    ImageResource bold();

    ImageResource createLink();

    ImageResource hr();

    ImageResource indent();

    ImageResource insertImage();

    ImageResource italic();

    ImageResource justifyCenter();

    ImageResource justifyLeft();

    ImageResource justifyRight();

    ImageResource ol();

    ImageResource outdent();

    ImageResource removeFormat();

    ImageResource removeLink();

    ImageResource strikeThrough();

    ImageResource subscript();

    ImageResource superscript();

    ImageResource ul();

    ImageResource underline();

    ImageResource mathify();
}