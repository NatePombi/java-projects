package model;

public enum TransactionType {
    DEPOSIT,WITHDRAW;

    public static TransactionType getEnum(String type){
        return switch (type.toLowerCase()){
            case "deposit"-> TransactionType.DEPOSIT;
            case "withdraw"-> TransactionType.WITHDRAW;
            default -> throw new IllegalArgumentException("Invalid Transaction Type");
        };
    }
}
