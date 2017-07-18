package dao;

import java.util.Set;
import model.Admin;
import model.Comment;
import model.Gallery;
import model.Item;

public interface AdminServices {

	public void createAdmin(Admin admin);
	
	public void removeAdmin(Admin admin);
	
	public void updateAdmin(Admin admin);
	
	public Set<Gallery> getGalleries(int adminId);
	
	public void createGallery(Admin admin, Gallery gallery);
	
	public void removeGallery(Gallery gallery);
	
	public void update(Gallery gallery);
	
	public Set<Item> getItems(int galleryId);
	
	public void createItem(Gallery gallery, Item item);
	
	public void removeItem(Item item);
	
	public void updateItem(Item item);
	
	public Set<Comment> getComment(int itemId);
}
