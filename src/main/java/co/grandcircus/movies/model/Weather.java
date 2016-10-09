package co.grandcircus.movies.model;

/**
 * Responsibility: Hold weather data for a point in time
 */
public class Weather {

	/**
	 * URL of image depicting weather
	 */
	private String image;
	/**
	 * Temperature in degrees Fahrenheit
	 */
	private Integer temperature;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getTemperature() {
		return temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

}