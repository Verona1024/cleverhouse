package verona1024.cleverhouse.database;

/**
 * Created by verona1024 on 22.03.15.
 */
public class RoomItem {
    private String name;
    private int temperature;
    private int weatnes;
    private int doors;
    private int balcon;
    private int windows;

    private int lights;

    public RoomItem(){}

    public RoomItem(String name, int temperature, int weatnes, int doors, int balcon, int windows, int lights){
        this.name = name;
        this.temperature = temperature;
        this.weatnes = weatnes;
        this.doors = doors;
        this.balcon = balcon;
        this.windows = windows;
        this.lights = lights;
    }

    public String getName() {
        return name;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getWeatnes() {
        return weatnes;
    }

    public int getDoors() {
        return doors;
    }

    public int getBalcon() {
        return balcon;
    }

    public int getWindows() {
        return windows;
    }

    public int getLights() {
        return lights;
    }

    public void setLights(int lights) {
        this.lights = lights;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public void setWeatnes(int weatnes) {
        this.weatnes = weatnes;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public void setBalcon(int balcon) {
        this.balcon = balcon;
    }

    public void setWindows(int windows) {
        this.windows = windows;
    }
}
