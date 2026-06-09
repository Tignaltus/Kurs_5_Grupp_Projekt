DELETE FROM loans;
DELETE FROM books;
DELETE FROM authors;

INSERT INTO authors (id, name)
VALUES (1, 'Shindou Masaoki');

INSERT INTO books (id, title, isbn, published_year, author_id)
VALUES (1, 'Ruri Dragon', '11111', 2022, 1);