package Entities;

public class Route {
    private String routeId;
    private String fromPlace;
    private String toPlace;
    private Double distance;

    public Route(String routeId, String fromPlace, String toPlace, Double distance) {
        this.routeId = routeId;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.distance = distance;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace;
    }

    public String getToPlace() {
        return toPlace;
    }

    public void setToPlace(String toPlace) {
        this.toPlace = toPlace;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route{" +
                "routeId='" + routeId + '\'' +
                ", fromPlace='" + fromPlace + '\'' +
                ", toPlace='" + toPlace + '\'' +
                ", distance=" + distance +
                '}';
    }
}
