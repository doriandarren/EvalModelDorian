package dao;

import java.util.HashSet;
import java.util.Set;

import model.Admin;
import model.Comment;
import model.Gallery;
import model.Item;

public class DBAdmin extends DBManager implements AdminServices{

	@Override
	public void createAdmin(Admin admin) {
		// La persona no tiene que ser NULL
		if (admin == null)
			throw new RuntimeException("Error: El Admin no puede ser NULL");
		
		connect();
		entitymanager.getTransaction().begin();	
			getEntityManager().persist(admin);						
		entitymanager.getTransaction().commit();
		close();		
	}

	@Override
	public void removeAdmin(Admin admin) {
		if (admin == null)
			throw new RuntimeException("Error: El Admin no puede ser NULL.");
		
		connect();		
		entitymanager.getTransaction().begin();
			Admin adminFind = entitymanager.find(Admin.class, admin.getId());
			entitymanager.remove(adminFind);		
		entitymanager.getTransaction().commit();		
		close();		
	}

	@Override
	public void updateAdmin(Admin admin) {
		if (admin == null)
			throw new RuntimeException("Error: El Admin no puede ser NULL.");
		connect();		
		entitymanager.getTransaction().begin();		
			Admin adminFind = entitymanager.find(Admin.class, admin.getId());
			adminFind.setName(admin.getName());			
		entitymanager.getTransaction().commit();
		close();
		
	}

	@Override
	public Set<Gallery> getGalleries(int adminId) {
		Set<Gallery> list = null;
		connect();		
		entitymanager.getTransaction().begin();		
			Admin adminFind = entitymanager.find(Admin.class, adminId);		
			list = new HashSet<Gallery>(adminFind.getGalleries());	
		entitymanager.getTransaction().commit();
		close();		
		return list;
	}

	@Override
	public void createGallery(Admin admin, Gallery gallery) {
		if (admin == null)
			throw new RuntimeException("Error: El Admin no puede ser NULL.");
		
		if(gallery!=null && gallery.getId()!=0)
			throw new RuntimeException("Error: la Gallery tiene que tener un ID diferente de cero."
					+ " Verfique la gallery, ya existe un registro");
		
		connect();
		entitymanager.getTransaction().begin();				
			Admin adminFind = entitymanager.find(Admin.class, admin.getId());			
			getEntityManager().persist(gallery);			
			gallery.setAdmin(adminFind);
			adminFind.getGalleries().add(gallery);			
		entitymanager.getTransaction().commit();
		close();
	}

	@Override
	public void removeGallery(Gallery gallery) {
		if (gallery == null)
			throw new RuntimeException("Error: La gallery no puede ser NULL.");
		
		connect();		
		entitymanager.getTransaction().begin();
			Gallery galleryFind = entitymanager.find(Gallery.class, gallery.getId());
			
			Admin admin = galleryFind.getAdmin();
			
			admin.getGalleries().remove(galleryFind);
			
			entitymanager.remove(galleryFind);
			
		entitymanager.getTransaction().commit();
		close();
		
	}

	@Override
	public void update(Gallery gallery) {
		if (gallery == null)
			throw new RuntimeException("Error: La Gallery no puede ser NULL.");
		
		connect();		
		entitymanager.getTransaction().begin();
			Gallery galleryFind = entitymanager.find(Gallery.class, gallery.getId());						
			galleryFind.setName(gallery.getName());
			galleryFind.setDescription(gallery.getDescription());			
		entitymanager.getTransaction().commit();
		close();		
	}

	@Override
	public Set<Item> getItems(int galleryId) {
		Set<Item> list = null; 
		connect();		
		entitymanager.getTransaction().begin();
			Gallery galleryFind = entitymanager.find(Gallery.class, galleryId);				
			list = new HashSet<Item>(galleryFind.getItems());	
		entitymanager.getTransaction().commit();
		close();		
		return list;
	}

	@Override
	public void createItem(Gallery gallery, Item item) {
		
		if (gallery == null)
			throw new RuntimeException("Error: La gallery no puede ser NULL.");
		
		if(item!=null && item.getId()!=0)
			throw new RuntimeException("Error: el Item tiene que tener un ID diferente de cero."
					+ " Verfique que el Item, ya existe un registro");
		
		connect();
		entitymanager.getTransaction().begin();			
			
			Gallery galleryFind = entitymanager.find(Gallery.class, gallery.getId());
			
			item.setGallery(galleryFind);
			galleryFind.getItems().add(item);	
						
		entitymanager.getTransaction().commit();
		close();		
	}

	@Override
	public void removeItem(Item item) {
		
		if (item == null)
			throw new RuntimeException("Error: el Item no puede ser NULL.");
		
		connect();		
		entitymanager.getTransaction().begin();
			Item itemFind = entitymanager.find(Item.class, item.getId());
			
			Gallery galleryFind = itemFind.getGallery();
			
			galleryFind.getItems().remove(itemFind);
			
			entitymanager.remove(itemFind);
			
		entitymanager.getTransaction().commit();
		close();
		
	}

	@Override
	public void updateItem(Item item) {
		if (item == null)
			throw new RuntimeException("Error: El item no puede ser NULL.");
		
		connect();		
		entitymanager.getTransaction().begin();
			Item itemFind = entitymanager.find(Item.class, item.getId());						
			itemFind.setName(item.getName());
			itemFind.setDescription(item.getDescription());			
		entitymanager.getTransaction().commit();
		close();
		
	}

	@Override
	public Set<Comment> getComment(int itemId) {
		Set<Comment> list = null; 
		connect();		
		entitymanager.getTransaction().begin();
			Item itemFind = entitymanager.find(Item.class, itemId);				
			list = new HashSet<Comment>(itemFind.getComments());	
		entitymanager.getTransaction().commit();
		close();		
		return list;
	}

	

}
