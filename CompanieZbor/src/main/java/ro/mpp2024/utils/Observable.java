package ro.mpp2024.utils;

public interface Observable {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}
