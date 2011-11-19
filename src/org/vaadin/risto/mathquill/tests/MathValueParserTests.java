package org.vaadin.risto.mathquill.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.vaadin.risto.mathquill.MathLabel;
import org.vaadin.risto.mathquill.MathValueParser;
import org.vaadin.risto.mathquill.RichMathArea;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class MathValueParserTests {

    private static final String MATHELEMENT = "<img src=\"http://latex.codecogs.com/gif.latex?2x\" title=\"2x\" class=\"latexPlaceHolder\" style=\"cursor: pointer;\">";
    private static final String TEXTELEMENT = "<div>foo</div>";
    private MathValueParser instance;

    @Before
    public void init() {
        instance = MathValueParser.get();
    }

    @Test
    public void parseValueToComponentsWithOneMathElement() {
        String mathvalue = MATHELEMENT;
        List<Component> result = instance
                .getMathValueAsComponents(getMathAreaWithValue(mathvalue));

        assertEquals(1, result.size());
        Component firstResult = result.get(0);
        assertMathComponent(firstResult);
    }

    @Test
    public void parseValueToComponentsWithOneTextElement() {
        String mathvalue = TEXTELEMENT;
        List<Component> result = instance
                .getMathValueAsComponents(getMathAreaWithValue(mathvalue));

        assertEquals(1, result.size());
        Component firstResult = result.get(0);
        assertTextComponent(firstResult);
    }

    @Test
    public void parseValueToComponentsWithTextAndMathElement() {
        String mathvalue = TEXTELEMENT + MATHELEMENT;
        List<Component> result = instance
                .getMathValueAsComponents(getMathAreaWithValue(mathvalue));

        assertEquals(2, result.size());
        Component firstResult = result.get(0);
        assertTextComponent(firstResult);
        Component secondResult = result.get(1);
        assertMathComponent(secondResult);
    }

    @Test
    public void parseValueToComponentsWithMathElementAndText() {
        String mathvalue = MATHELEMENT + TEXTELEMENT;
        List<Component> result = instance
                .getMathValueAsComponents(getMathAreaWithValue(mathvalue));

        assertEquals(2, result.size());
        Component firstResult = result.get(0);
        assertMathComponent(firstResult);
        Component secondResult = result.get(1);
        assertTextComponent(secondResult);
    }

    @Test
    public void parseValueToComponentsWithABunchOfContent() {
        String mathvalue = TEXTELEMENT + MATHELEMENT + TEXTELEMENT
                + MATHELEMENT + TEXTELEMENT;
        List<Component> result = instance
                .getMathValueAsComponents(getMathAreaWithValue(mathvalue));

        assertEquals(5, result.size());
        assertTextComponent(result.get(0));
        assertMathComponent(result.get(1));
        assertTextComponent(result.get(2));
        assertMathComponent(result.get(3));
        assertTextComponent(result.get(4));
    }

    @Test
    public void parseValueToMathWithOneMathElement() {
        String mathvalue = MATHELEMENT;
        String result = instance.getMathValue(getMathAreaWithValue(mathvalue));

        assertEquals("2x", result);
    }

    @Test
    public void parseValueToMathWithOneTextElement() {
        String mathvalue = TEXTELEMENT;
        String result = instance.getMathValue(getMathAreaWithValue(mathvalue));

        assertEquals(TEXTELEMENT, result);
    }

    @Test
    public void parseValueToMathWithTextAndMathElement() {
        String mathvalue = TEXTELEMENT + MATHELEMENT;
        String result = instance.getMathValue(getMathAreaWithValue(mathvalue));

        assertEquals(TEXTELEMENT + "2x", result);
    }

    @Test
    public void parseValueToMathWithMathElementAndText() {
        String mathvalue = MATHELEMENT + TEXTELEMENT;
        String result = instance.getMathValue(getMathAreaWithValue(mathvalue));

        assertEquals("2x" + TEXTELEMENT, result);
    }

    @Test
    public void parseValueToMathWithABunchOfContent() {
        String mathvalue = TEXTELEMENT + MATHELEMENT + TEXTELEMENT
                + MATHELEMENT + TEXTELEMENT;
        String result = instance.getMathValue(getMathAreaWithValue(mathvalue));

        assertEquals(TEXTELEMENT + "2x" + TEXTELEMENT + "2x" + TEXTELEMENT,
                result);
    }

    protected void assertMathComponent(Component firstResult) {
        assertNotNull(firstResult);
        assertTrue(firstResult instanceof MathLabel);
        MathLabel mathLabel = (MathLabel) firstResult;
        assertEquals("2x", mathLabel.getValue());
    }

    protected void assertTextComponent(Component firstResult) {
        assertNotNull(firstResult);
        assertTrue(firstResult instanceof Label);
        Label mathLabel = (Label) firstResult;
        assertEquals(TEXTELEMENT, mathLabel.getValue());
    }

    private RichMathArea getMathAreaWithValue(String mathvalue) {
        RichMathArea area = new RichMathArea();
        area.setValue(mathvalue);
        return area;
    }
}
