    1. Phân tích vấn đề

Vi phạm Single Responsibility Principle (SRP): OrderService đang "ôm đồm" quá nhiều trách nhiệm: quản lý kho, tính toán giảm giá, xử lý thanh toán, gửi thông báo, và cả log/analytics. Một thay đổi nhỏ ở logic gửi SMS cũng có thể làm hỏng logic thanh toán.

Vi phạm Open/Closed Principle (OCP): Logic giảm giá và thanh toán được viết cứng bằng if-else. Mỗi khi có chương trình khuyến mãi mới (ví dụ: Black Friday) hoặc cổng thanh toán mới (ví dụ: Crypto), ta buộc phải sửa đổi trực tiếp vào code lõi.

Vi phạm Dependency Inversion Principle (DIP): Class phụ thuộc trực tiếp vào các implementation cụ thể thay vì abstraction.

Vấn đề Type Safety: Việc sử dụng Object[] là một rủi ro lớn. Nó làm mất đi sức mạnh của trình biên dịch (Compiler), dễ dẫn đến lỗi NullPointerException hoặc ClassCastException khi chạy (Runtime).

    2. Tư duy xử lý

Bước 1: Domain Modeling
Thay thế Object[] bằng các POJO (Plain Old Java Objects) như Product, Order, Customer. Điều này giúp code có tính tự thuyết minh (self-documenting) và bảo mật kiểu dữ liệu.

Bước 2: Áp dụng Strategy Pattern (Chiến lược linh hoạt)
Discount Strategy: Tách các quy tắc giảm giá ra các class riêng biệt. Hệ thống chỉ cần biết nó đang áp dụng một "chiến lược giảm giá", không cần quan tâm đó là giảm giá cho Điện tử hay Thực phẩm.

Payment Strategy: Tương tự, mỗi phương thức thanh toán (Visa, PayPal, Crypto) sẽ là một implementation của interface PaymentProcessor.

Bước 3: Áp dụng Dependency Injection (DI)
OrderService sẽ không tự khởi tạo các dịch vụ phụ trợ. Thay vào đó, các dịch vụ như NotificationService hay InventoryManager sẽ được "bơm" (inject) vào qua Constructor. Điều này giúp việc Unit Test trở nên cực kỳ dễ dàng bằng cách sử dụng Mock objects.
