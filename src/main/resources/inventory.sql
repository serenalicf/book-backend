DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory (
   entry_id INT AUTO_INCREMENT PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   author VARCHAR(255) NOT NULL,
   genre VARCHAR(255) NOT NULL,
   publication_date DATE NOT NULL,
   isbn VARCHAR(20) UNIQUE NOT NULL
)

INSERT INTO inventory (title,author,genre,publication_date,isbn)
VALUES ('Cloud Native Spring in Action','Thomas Vitale','Technology, Computer Science, and Programming','2022-12-20','1617298425'),
    ('Introduction to Algorithms','Thomas H. Cormen','Computer Science','2022-04-05','9780262046305'),
    ('Design Patterns: Elements of Reusable Object-Oriented Software','Erich Gamma','Software Development','1994-01-01','0201633612'),
    ('Computer Networking: A Top-Down Approach','James Kurose','Information Technology','2016-04-26','9780133594140'),
    ('Spring Security in Action','Laurentiu Spilca','Technology, Computer Science, and Programming','2024-05-14',' 9781633437975'),
    ('Spring Boot in Action','Craig Walls','Technology, Computer Science, and Programming','2015-12-16',' 9781617292545')
    ('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 'Fantasy', '1997-06-26', '9780590353427'),
('Harry Potter and the Chamber of Secrets', 'J.K. Rowling', 'Fantasy', '1998-07-02', '9780439064866'),
('Harry Potter and the Prisoner of Azkaban', 'J.K. Rowling', 'Fantasy', '1999-09-08', '9780439136365'),
('Harry Potter and the Goblet of Fire', 'J.K. Rowling', 'Fantasy', '2000-07-08', '9780439139601'),
('Harry Potter and the Order of the Phoenix', 'J.K. Rowling', 'Fantasy', '2003-06-21', '9780439358071'),
('Harry Potter and the Half-Blood Prince', 'J.K. Rowling', 'Fantasy', '2005-07-16', '9780439785969'),
('Harry Potter and the Deathly Hallows', 'J.K. Rowling', 'Fantasy', '2007-07-21', '9780545010221'),
    ('The Lion, the Witch and the Wardrobe', 'C.S. Lewis', 'Fantasy', '1950-10-16', '9780064471046'),
('Prince Caspian', 'C.S. Lewis', 'Fantasy', '1951-10-15', '9780064471053'),
('The Voyage of the Dawn Treader', 'C.S. Lewis', 'Fantasy', '1952-09-15', '9780064471077'),
('The Silver Chair', 'C.S. Lewis', 'Fantasy', '1953-09-07', '9780064471091'),
('The Horse and His Boy', 'C.S. Lewis', 'Fantasy', '1954-09-06', '9780064471107'),
('The Magician''s Nephew', 'C.S. Lewis', 'Fantasy', '1955-05-02', '9780064471114'),
('The Last Battle', 'C.S. Lewis', 'Fantasy', '1956-09-04', '9780064471121')



