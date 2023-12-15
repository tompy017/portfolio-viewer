package perlas.de.portfolio.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
//@Data  // will auto generate constructor, hashCode, toString, getters & setters ~ @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode
@Table(name = "investments")
//@NoArgsConstructor
//@AllArgsConstructor
public class Investment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "qty")
	private double qty;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "purchased_date")
	private LocalDate purchasedDate;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "type")
	private String type;
	
	@Column(columnDefinition = "varchar(10) default 'usd'")
	private String currency;
	
	@Column(name = "actual_price")
	private double actualPrice;
	
	//actual value in usd to make calculations
	@Column(name = "usd_actual_price")
	private double actualPriceInUSD;

	public double getActualPriceInUSD() {
		return actualPriceInUSD;
	}

	public void setActualPriceInUSD(double actualPriceInUSD) {
		this.actualPriceInUSD = actualPriceInUSD;
	}

	public double getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(double actualPrice) {
		this.actualPrice = actualPrice;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getQty() {
		return qty;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDate getPurchasedDate() {
		return purchasedDate;
	}

	public void setPurchasedDate(LocalDate purchasedDate) {
		this.purchasedDate = purchasedDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Investment [id=" + id + ", token=" + token + ", name=" + name + ", qty=" + qty + ", price=" + price
				+ ", purchasedDate=" + purchasedDate + ", actualPrice=" +actualPrice + ", category=" + category + ", type=" + type + "]";
	}

	
	public Investment() {}

	public Investment(int id, String token, String name, double qty, double price, LocalDate purchasedDate,
			String category, String type, String currency) {
		super();
		this.id = id;
		this.token = token;
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.purchasedDate = purchasedDate;
		this.category = category;
		this.type = type;
		this.currency = currency;
		this.actualPrice = price;
	}
	
	
	//without purchasedDate -> will use today's date as purchased date
	public Investment(int id, String token, String name, double qty, double price, String category, String type, String currency) {
		super();
		this.id = id;
		this.token = token;
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.purchasedDate = LocalDate.now();
		this.category = category;
		this.type = type;
		this.currency = currency;
		this.actualPrice = price;
	}
	

	
}
