package model;

public class Room implements IRoom {
    private String roomNumber;
    private Double roomPrice;
    private RoomType roomType;


    public Room(String roomNumber,Double roomPrice,RoomType roomType){
        this.roomNumber=roomNumber;
        this.roomPrice=roomPrice;
        this.roomType=roomType;


    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString(){
        return "Room number: " + roomNumber + ", Room type : "+ roomType +", Price: " + roomPrice;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){return true;}
        if(this.getClass()!=obj.getClass()){return false;}
        Room room = (Room) obj;
        return this.getRoomNumber()==room.getRoomNumber()
                && this.getRoomPrice()== room.getRoomPrice()
                && this.getRoomType()== room.getRoomType();
    }

    @Override
    public int hashCode(){
            int result = 13;
            result = result + (roomNumber!=null? roomNumber.hashCode():0);
            result += roomPrice.intValue()+(roomType!=null? roomType.hashCode():0);
            return result;
    }
}
