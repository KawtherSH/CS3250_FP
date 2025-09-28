
public class Settings {
	
	// variables 
	private int volume = 70;
    private boolean fullscreen = false;


	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}
	
	// Methods 
		public void settingsMenu() {
	        System.out.println("Display settings here.");
	    }
	
}
