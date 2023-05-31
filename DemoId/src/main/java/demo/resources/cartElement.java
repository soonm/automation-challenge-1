package demo.resources;

public class CartElement {
	private float kcal;
	private float protein;
	private float fat;
	private float carbs;

	public CartElement(float kcal, float protein,float fat,float carbs) {
		this.kcal = kcal;
	    this.protein = protein;
	    this.fat=fat;
	    this.carbs=carbs;
	}
	    
	public float getKcal() {
	    return kcal;
	}

	public float getProtein() {
		return protein;
	}
	
	public float getFat() {
	    
		return fat;
	}
	
	public float getCarbs() {
	    
		return carbs;
	}
}
