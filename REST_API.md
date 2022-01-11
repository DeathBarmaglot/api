##level 1:
####� ��� ���� �������� ��������.
� ���� -> id, title, content
GET /api/v1/posts - ��������� ���� ������
POST /api/v1/posts - ���������� ������ �����, ���� ���������� � ������� JSON
PUT /api/v1/posts/{id} - ����������� �����, ����� ������ ����� ���������� � ������� JSON
DELETE /api/v1/posts/{id} - �������� ����� �� id
���������� Spring boot + Spring Data Jpa
##level 2:
####��������� ��� ���� ������� ����� MockMvc, � ���������� ��������� ������ ����� �� ��������� ������ �����������.
��������� ��������� ��� ���������� ��������
GET /api/v1/posts?title=:title - ����� ������ � ������� :title
GET /api/v1/posts?sort=title - ���������� ��� ����� ��������������� �� ������
###level 3:
��������� � �������� ���� ���� star. � ������� ���� �� ����� ��������� ������� ��� ������.
GET /api/v1/posts/star - �������� ��� ��� �����.
PUT /api/v1/posts/{id}/star - �������� ���� ��� ���.
DELETE /api/v1/posts/{id}/star - ������ ������� ��� �����.
###level 4:
����������� �������� ����������� (�� ������ ������ ����������)
id, text, creationDate(�� ���������� � ������� � ������������ � ����������), postId
REST ������� ��� ������ � ����������
POST /api/v1/posts/1/comments
GET /api/v1/posts/{id}/comments
GET /api/v1/posts/{postId}/comment/{commentId}
###level 5:
GET /api/v1/posts/1/full - ���������� JSON ����� � �� = 1, 
� ����� ���������� ������������� � ����