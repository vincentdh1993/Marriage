import java.util.*;

public class Match {
	private String name;
	private ArrayList<Integer> DreamMatch;
	private ArrayList<Integer> RealMatch;
	private int partner; //as all the people has their number.
	
	//Constructor that takes the name and the matching number list.
	public Match(String name, List<Integer> match) {
		setName(name);
		setDreamMatch(match);
		setMatch(match);
	}
	
	//set and get methods for parameters. (encapsulating)
	
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setDreamMatch(List<Integer> match){
		DreamMatch = new ArrayList<Integer>();
		for (int i : match) {
			DreamMatch.add(i);
		}
	}
	
	public List<Integer> getDreamMatch(){
		return this.DreamMatch;
	}
	
	public void setMatch(List<Integer> match){
		RealMatch = new ArrayList<Integer>();
		for (int i : match) {
			RealMatch.add(i);
		}
	}
	
	public List<Integer> getMatch(){
		return this.RealMatch;
	}
	
	public void setPartner(int partner){
		this.partner = partner;
	}
	
	public int getPartner(){
		return this.partner;
	}
	
	
	//method that checks if the person is free. free as -1
	public boolean isFree(){
		return (this.getPartner() == -1);
	}
	
	//make the people free.
	public void free(){
		this.partner = -1;
	}
	
	
	//make match by setting partner.
	public void success(int match){
		setPartner(match);
	}
}
