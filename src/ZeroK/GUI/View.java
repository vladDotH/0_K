package ZeroK.GUI;

import java.awt.*;

abstract public class View {

    protected String name;

    public String getName() {
        return name;
    }

    abstract protected Component getJComponent();
}
