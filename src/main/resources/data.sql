insert into roles (id, user_role)
values (1, 'ADMIN'),
       (2, 'USER');

insert into users (id, username, email, password)
values (1, 'Admin', 'admin@admin.com', '$2a$10$HzqnEszPXL962Yo0WFQFQu6mT2gDr/jDyZeheSmvHISlGxD/PqH.C');

insert into recipes (ingredients, method_of_preparation)
values ('Beef burger (380g)  ingredients', 'Beef burger (380g) - method of preparation'),
       ('Beef burger (290g)  ingredients', 'Beef burger (290g) - method of preparation'),
       ('Chicken cheeseburger ingredients', 'Chicken cheeseburger - method of preparation'),
       ('Pepperoni Pizza ingredients', 'Pepperoni Pizza - method of preparation'),
       ('Pizza napoletana ingredients', 'bPizza napoletana - method of preparation'),
       ('Greek Pizza Recipe ingredients', 'Greek Pizza Recipe - method of preparation'),
       ('Pasta with cherry tomatoes', 'Pasta with cherry tomatoes - method of preparation'),
       ('Pasta Carbonara ingredients', 'Pasta Carbonara - method of preparation'),
       ('Red Curry Pasta', 'Red Curry Pasta - method of preparation');
;

insert into products (name, description, price, image_url, product_type, recipe_id)
values ('Beef burger (380g)', 'Brioche bun, ground beef, blue cheese melted on crispy bacon, BBQ sauce and fries', 10, 'product-pictures/burger-1.jpg', 'BURGER', 1),
       ('Pork burger (290g)', 'Pulled pork, iceberg, tomato, egg, cheddar and sauce', 20, 'product-pictures/burger-2.jpg', 'BURGER', 2),
       ('Chicken cheeseburger (280g)', 'Crispy chicken fillet, lettuce, cheese mousse, tomato and cheddar', 30, 'product-pictures/burger-3.jpg', 'BURGER', 3),
       ('Pepperoni Pizza', 'Thinly sliced pepperoni, freshly shredded cheese and topped with herbs and spices', 10, 'product-pictures/pizza-1.jpg', 'PIZZA', 4),
       ('Pizza napoletana', 'Tomato puree scented with oregano, anchovies in oil and caper berries', 20, 'product-pictures/pizza-2.jpg', 'PIZZA', 5),
       ('Greek Pizza', 'Focaccia, drenched in olive oil, and topped with tomatoes, onions, and feta', 30, 'product-pictures/pizza-3.jpg', 'PIZZA', 6),
       ('Pasta with cherry tomatoes', 'Summery cherry tomatoes, crushed red pepper and fresh basil', 10, 'product-pictures/pasta-1.jpg', 'PASTA', 7),
       ('Pasta Carbonara', 'Silky cheese sauce with crisp pancetta and black pepper', 20, 'product-pictures/pasta-2.jpg', 'PASTA', 8),
       ('Red Curry Pasta', 'Red curry sauce tossed in a combination of assorted vegetables', 30, 'product-pictures/pasta-3.jpg', 'PASTA', 9);

insert into users_roles (user_id, role_id)
values (1, 2),
       (1, 1);

