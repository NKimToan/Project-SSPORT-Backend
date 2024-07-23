-- create database SSPORT;

use SSPORT;

select * from admin;

select * from token
order by expiry_time;

select * from customer;

select * from product
order by category_id, cost DESC;

select * from category;

select * from promotion;

select * from category_promotion;

select * from review;

select * from customer;
select * from product;
select * from category;
select * from cart;
select * from invoice;
select * from invoice_cart;

select product_name, cost, p.price, c.quantity, total_price, username as"Người mua"
from product p inner join cart c on p.product_id = c.product_id
				inner join customer cu on c.customer_id = cu.customer_id;


select category_name, product_name, cost, discount, date_created, date_end, price
from category c inner join product p on c.category_id = p.category_id
				inner join category_promotion cp on c.category_id = cp.category_category_id
                inner join promotion pr on cp.promotion_promotion_id = pr.promotion_id
where (date_created <= current_date() and date_end > current_date() and price = cost * (1-discount)) or (price = cost);

select category_name, image, promotion_name, promotion_name, discount
from category_promotion cp inner join category c on cp.category_category_id = c.category_id
						inner join promotion p on cp.promotion_promotion_id = p.promotion_id
where c.category_id = 6;
