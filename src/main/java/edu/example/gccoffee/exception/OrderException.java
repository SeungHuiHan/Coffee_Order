package edu.example.gccoffee.exception;

public enum OrderException {
    NOT_REGISTERED("ORDER NOT Registered", 400),
    NOT_FOUND_ORDER("ORDER NOT FOUND",404),
    NOT_FOUND_ORDERITEM("ORDERItem NOT FOUND",404),
    NOT_FOUND_PRODUCT("Product NOT FOUND for ORDER",404),
    FAIL_ADD("ORDER Add Fail",400),

    FAIL_MODIFY("ORDER Modify Fail",404),
    FAIL_REMOVE("ORDER Remove Fail",400),

    NOT_FETCHED("ORDER NOT Fetched",400),
    NOT_MATCHED("ORDER NOT matched",400),
    NOT_MATCHED_CUSTOMER("Customer NO Matched",400),
    NOT_MATCHED_ORDERITEM("OrderItem NO Matched",400);

    private OrderTaskException OrderTaskException; //memberTaskException 필드는 해당 enum 상수에 대응하는 예외 객체를 저장한다.

    OrderException(String message, int code) {
        OrderTaskException = new OrderTaskException(message, code);
    }
//이 생성자는 enum 상수가 선언될 때 호출되며, message와 code를 인수로 받아 MemberTaskException 객체를 생성하여 memberTaskException 필드에 저장한다.
//예를 들어, NOT_FOUND 상수가 선언될 때 "NOT_FOUND"와 404가 인수로 전달되어 new ORDERTaskException("NOT_FOUND", 404)가 생성됩니다.

    public OrderTaskException get() {
        return OrderTaskException;
    }
    
}
