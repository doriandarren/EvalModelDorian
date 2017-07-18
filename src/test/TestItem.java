package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBAdmin;
import model.Admin;
import model.Gallery;
import model.Item;

public class TestItem {

	DBAdmin dbConnector; 

	@Before
	public void init(){
		dbConnector =  new DBAdmin(); 	
		dbConnector.connect();
		dbConnector.deleteAll(Admin.class);
		dbConnector.close();
	}
	
	
	//@Test(expected=RuntimeException.class)
	public void testcreateItemGalleryNull() {
		/**
		 * Lanza una exception
		 */
		//Admin admin = getMockAdmin("Administrador"); 
		Gallery gallery = null;
		Item item = getMosckItem("New Item 1", "Descripcion New Item 1", 5.63f);		
		dbConnector.createItem(gallery, item);		
	}

	//@Test(expected=RuntimeException.class)
	public void testCreateItemNull() {
		//Admin admin = getMockAdmin("Administrador");
		Gallery gallery = getMockGallery("Galeria 1", "Comentario Galeria 1");		
		Item item = null;		
		dbConnector.createItem(gallery, item);
	}
	
	
	
	
	//@Test
	public void testCreateItem(){
		
		Admin admin = getMockAdmin("Administrador");
		Gallery gallery = getMockGallery("Galeria 1", "Comentario Galeria 1");
		
		dbConnector.connect();
		dbConnector.getEntityManager().getTransaction().begin();
			dbConnector.getEntityManager().persist(admin);
			gallery.setAdmin(admin);
			admin.getGalleries().add(gallery);			
		dbConnector.getEntityManager().getTransaction().commit();
		dbConnector.close();
		
		Item item = getMosckItem("New Item 1", "Descripcion New Item 1", 5.63f);
		
		
		dbConnector.createItem(gallery, item);
		
		
		dbConnector.connect();
			Gallery galletyRecovered = dbConnector.find(Gallery.class, gallery.getId());
		dbConnector.close();
		
		Assert.assertNotNull(galletyRecovered);
		Assert.assertEquals(true,galletyRecovered.getItems().iterator().next().getId()>0);
		Assert.assertEquals("New Item 1",galletyRecovered.getItems().iterator().next().getName());
		
	}
	
	
	
	@Test
	public void testUpdateGallery() {

		Admin admin = getMockAdmin("Administrador");
		Gallery gallery = getMockGallery("Galeria 1", "Comentario Galeria 1");
		Item item = getMosckItem("New Item 1", "Descripcion New Item 1", 5.63f);
		
		dbConnector.connect();
		dbConnector.getEntityManager().getTransaction().begin();
			
			dbConnector.getEntityManager().persist(admin);
			
			gallery.setAdmin(admin);
			admin.getGalleries().add(gallery);	
			
			item.setGallery(gallery);
			gallery.getItems().add(item);
		dbConnector.getEntityManager().getTransaction().commit();
		dbConnector.close();

		
		dbConnector.connect();
		Gallery galleryUpdate = dbConnector.find(Gallery.class, gallery.getId());
		dbConnector.close();
		
		galleryUpdate.getItems().iterator().next().setName("Update");
		
				
		dbConnector.updateItem(item);
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	public Admin getMockAdmin(String name){
		Admin admin = new Admin();
		admin.setName(name);
		return admin;
	}
	
	public Gallery getMockGallery(String name, String galeria){
		Gallery gallery = new Gallery();
		gallery.setName(name);
		gallery.setDescription(galeria);
		return gallery;
	}
	
	public Item getMosckItem(String name, String description, float price){
		Item item = new Item();
		item.setName(name);
		item.setDescription(description);
		item.setPrice(price);
		return item;
	}
	
}
