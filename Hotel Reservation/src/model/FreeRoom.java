package model;

public class FreeRoom extends Room{
    private String roomNumber;
    private Double roomPrice=0.0;
    private RoomType roomType;


    public FreeRoom(String roomNumber, Double roomPrice, RoomType roomType) {
        super(roomNumber, roomPrice, roomType);
        roomPrice = this.roomPrice;
    }

    @Override
    public boolean isFree(){
        return true;
    }


    @Override
    public String toString(){
        return "Room number : "+roomNumber+ " " +roomType +"This room is free";
    }

}
