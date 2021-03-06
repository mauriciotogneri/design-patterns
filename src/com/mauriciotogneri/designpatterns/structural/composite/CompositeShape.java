package com.mauriciotogneri.designpatterns.structural.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeShape implements Shape
{
    private final List<Shape> children = new ArrayList<>();

    @Override
    public void draw()
    {
        for (Shape shape : this.children)
        {
            shape.draw();
        }
    }

    public void add(Shape shape)
    {
        this.children.add(shape);
    }
}