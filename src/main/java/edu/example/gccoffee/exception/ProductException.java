package edu.example.gccoffee.exception;


public enum ProductException {
    NOT_FOUND("Product NOT FOUND",404),
    NOT_REGISTERED("Product NOT Registered",404),
    NOT_MODIFIED("Product NOT Modified",404),
    NOT_REMOVED("Product NOT Removed",400),
    NOT_FETCHED("Product NOT Fetched",400),
    NOT_IMAGE("Product NOT Image",400),
    REGISTER_ERR("Product NO iMAGE",403),
    BAD_CREDENTIALS("BAD_CREDENTIALS",401);

    private ProductTaskException productTaskException; //memberTaskException 필드는 해당 enum 상수에 대응하는 예외 객체를 저장한다.

    ProductException(String message, int code) {
        productTaskException = new ProductTaskException(message, code);
    }
//이 생성자는 enum 상수가 선언될 때 호출되며, message와 code를 인수로 받아 MemberTaskException 객체를 생성하여 memberTaskException 필드에 저장한다.
//예를 들어, NOT_FOUND 상수가 선언될 때 "NOT_FOUND"와 404가 인수로 전달되어 new ProductTaskException("NOT_FOUND", 404)가 생성됩니다.

    public ProductTaskException get() {
        return productTaskException;
    }
}
