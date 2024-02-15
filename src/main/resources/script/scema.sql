-- script 작성 완료
CREATE TABLE Addresses (
                           AddressID INT AUTO_INCREMENT,
                           UserID varchar(50) NOT NULL,
                           Street varchar(255) NOT NULL,
                           City varchar(50) NOT NULL,
                           State varchar(50),
                           ZipCode varchar(20),
                           PRIMARY KEY (AddressID),
                           FOREIGN KEY (UserID) REFERENCES Users(UserID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주소';

CREATE TABLE PointUsageHistory (
                                   PointHistoryID INT AUTO_INCREMENT PRIMARY KEY,
                                   UserID VARCHAR(50),
                                   OrderID INT,
                                   PointsUsed INT,
                                   RemainingPoints INT,
                                   OrderDate DATETIME,
                                   CONSTRAINT fk_PointUsageHistory_UserID FOREIGN KEY (UserID) REFERENCES Users(UserID),
                                   CONSTRAINT fk_PointUsageHistory_OrderID FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE Users (
                       UserID varchar(50) NOT NULL COMMENT '아이디',
                       UserName varchar(50) NOT NULL COMMENT '이름',
                       UserPassword varchar(200) NOT NULL COMMENT 'mysql password 사용',
                       UserBirth varchar(8) NOT NULL COMMENT '생년월일 : 19840503',
                       UserAuth varchar(10) NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
                       UserPoint int NOT NULL COMMENT 'default : 1000000',
                       CreatedAt datetime NOT NULL COMMENT '가입일자',
                       LatestLoginAt datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
                       Addresses INT DEFAULT 0 COMMENT 'Number of addresses resident can manage',
                       PRIMARY KEY (UserID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='회원';

CREATE TABLE Categories (
                            CategoryID		INT	auto_increment,
                            CategoryName	varchar(50),

                            CONSTRAINT pk_Categories PRIMARY KEY(CategoryID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='종류';

CREATE TABLE Products (
                          ProductID	INT	auto_increment,
                          CategoryID	INT,
                          ModelNumber	nvarchar(10),
                          ModelName	nvarchar(120),
                          ProductImage	nvarchar(30),
                          UnitCost	decimal(15),
                          Description	text,

                          CONSTRAINT pk_Products PRIMARY KEY(ProductID),
                          CONSTRAINT fk_Products_Categories FOREIGN KEY(CategoryID) REFERENCES Categories(CategoryID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='상품';

CREATE TABLE Reviews (
                         ReviewID	int auto_increment,
                         ProductID	int,
                         UserID	varchar(50),
                         Rating		int,
                         Comments	text,

                         CONSTRAINT pk_ReviewID PRIMARY KEY(ReviewID),
                         CONSTRAINT fk_Review_Products FOREIGN KEY(ProductID) REFERENCES Products(ProductID),
                         CONSTRAINT fk_Review_User FOREIGN KEY(UserID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='리뷰';

CREATE TABLE Orders (
                        OrderID		int auto_increment,
                        UserID	varchar(50),
                        OrderDate	Datetime,
                        ShipDate	Datetime,

                        CONSTRAINT pk_Orders PRIMARY KEY(OrderID),
                        CONSTRAINT fk_Orders_UserID FOREIGN KEY(UserID) REFERENCES Users(UserID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문';

CREATE TABLE OrderDetails (
                              OrderID		int,
                              ProductID	int,
                              Quantity	int,
                              UnitCost	decimal(15),

                              CONSTRAINT pk_OrderDetails PRIMARY KEY(OrderID, ProductID),
                              CONSTRAINT fk_OrderDetails_Orders FOREIGN KEY(OrderID) REFERENCES Orders(OrderID),
                              CONSTRAINT fk_OrderDetails_Products FOREIGN KEY(ProductID) REFERENCES Products(ProductID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주문상세';

CREATE TABLE ShoppingCart (
                              RecordID	int	auto_increment,
                              CartID		nvarchar(150),
                              Quantity	int,
                              ProductID	int,
                              DateCreated	Datetime DEFAULT NOW(),

                              CONSTRAINT pk_RecordID PRIMARY KEY(RecordID),
                              CONSTRAINT fk_cart_ProductID FOREIGN KEY(ProductID) REFERENCES Products(ProductID)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='장바구니';
