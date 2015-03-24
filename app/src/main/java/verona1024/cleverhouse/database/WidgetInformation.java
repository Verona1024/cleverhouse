package verona1024.cleverhouse.database;

/**
 * Created by verona1024 on 24.03.15.
 */
public class WidgetInformation {

    private int temperature;
    private int weatness;
    private boolean lights;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getWeatness() {
        return weatness;
    }

    public void setWeatness(int weatness) {
        this.weatness = weatness;
    }

    public boolean isLights() {
        return lights;
    }

    public void setLights(boolean lights) {
        this.lights = lights;
    }
}
