package orot.model;

public class Project {
	private String Name,Description,Picture;
	public Project(String name,String description,String picture){
		Name=name;
		Description=description;
		Picture=picture;
	}
	
	public String getName () {
		return Name;
	}
	
	public String getDescription () {
		return Description;
	}
	
	public String getPicture () {
		return Picture;
	}
}
