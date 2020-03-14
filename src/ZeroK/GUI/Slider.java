package ZeroK.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Slider extends View {

    private JSlider slider;
    private final Label sliderName;

    private static ArrayList<Slider> sliders = new ArrayList<>();

    public static void load() {
        JSONParser parser = new JSONParser();
        JSONObject data = null;

        try {
            data = (JSONObject) parser.parse(new FileReader("sliders_data.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        if (data != null) {
            for (Slider s : sliders) {
                s.slider.setValue(Math.toIntExact((Long) data.get(s.getName())));
            }
        }

        System.out.println("Sliders loaded successfully");
    }

    public static void save() {
        JSONObject data = new JSONObject();
        for (Slider s : sliders) {
            data.put(s.getName(), s.getValue());
        }

        try (FileWriter file = new FileWriter("sliders_data.json")) {
            file.write(data.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sliders saved successfully");
    }

    public Slider(String name, int min, int max, int init) {
        this.name = name;

        sliderName = new Label(name);
        sliderName.setMessage(Integer.toString(init));

        slider = new JSlider(JSlider.HORIZONTAL,
                min, max, init);

        slider.setMinorTickSpacing((max - min) / 10);
        slider.setPaintTicks(true);

        setChangeListener((e -> {
        }));

        sliders.add(this);
    }

    public Label getLabel() {
        return sliderName;
    }

    public void setChangeListener(ChangeListener listener) {
        slider.addChangeListener(e -> {
            listener.stateChanged(e);
            sliderName.setMessage("" + slider.getValue());
        });
    }

    public int getValue() {
        return slider.getValue();
    }

    @Override
    protected Component getJComponent() {
        return slider;
    }
}

