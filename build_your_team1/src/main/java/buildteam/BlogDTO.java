package buildteam;



public class BlogDTO {
    private int bid;
    private String title;
    private String description;
    private long createdDate;
    private String image; // Base64 string
    private UserDTO user;

    // Getters & Setters
    public int getBid() { return bid; }
    public void setBid(int bid) { this.bid = bid; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getCreatedDate() { return createdDate; }
    public void setCreatedDate(long createdDate) { this.createdDate = createdDate; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public UserDTO getUser() { return user; }
    public void setUser(UserDTO user) { this.user = user; }
	@Override
	public String toString() {
		return "BlogDTO [bid=" + bid + ", title=" + title + ", description=" + description + ", createdDate="
				+ createdDate + ", image=" + image + ", user=" + user + "]";
	}
    
    
    
    
}
