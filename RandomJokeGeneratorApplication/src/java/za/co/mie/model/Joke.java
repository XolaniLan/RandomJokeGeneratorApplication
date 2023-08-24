package za.co.mie.model;

public class Joke{
  private int category;
  private String joke;

  public Joke(){
  }

  public Joke(int category, String joke){
    this.category = category;
    this.joke = joke;
  }

  public int getCategory(){
    return category;
  }

  public void setCategory(int category){
    this.category = category;
  }

  public String getJoke(){
    return joke;
  }

  public void setJoke(String joke){
    this.joke = joke;
  }

  @Override
  public int hashCode(){
    int hash = 5;
    hash = 19 * hash + this.category;
    return hash;
  }

  @Override
  public boolean equals(Object obj){
    if(this == obj){
      return true;
    }
    if(obj == null){
      return false;
    }
    if(getClass() != obj.getClass()){
      return false;
    }
    final Joke other = (Joke) obj;
    return true;
  }

  @Override
  public String toString(){
    return "Joke{" + "category=" + category + ", joke=" + joke + '}';
  }
}
