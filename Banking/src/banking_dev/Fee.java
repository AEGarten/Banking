package banking_dev;
import java.io.Serializable;
import java.util.Date;

public class Fee implements Serializable {
	public Money amount;
	public Date date;
	public String type;

	public Fee(Date date, Money amount, String type) {
		this.date = date;
		this.amount = amount;
		this.type = type;
	}
	
	public String toString() {
		String out = date.getMonth() +"/"+ date.getDate() +"/"+ (date.getYear() - 100) + 
				" "+ date.getHours() +":"+ String.format("%02d", date.getMinutes()) +
				" $"+ amount +" "+ type;
		return out;
	}
}
