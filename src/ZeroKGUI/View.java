package ZeroKGUI;

abstract public class View {
    protected String name;

    public String getName() {
        return name;
    }

    abstract protected Object getJComponent();
}
