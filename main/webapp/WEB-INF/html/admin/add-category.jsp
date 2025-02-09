<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="baseHeader.jsp"%>
<style>
  form.category-add {
    display: flex;
    flex-direction: column;
    gap: 15px;
    margin: 20px auto;

  }
  input, select, button.add {
    padding: 10px;
    font-size: 16px;
    border: 1px solid #ccc;
    border-radius: 5px;
    width: 325px;
  }
  button.add {
    background-color: #4CAF50;
    color: white;
    border: none;
    cursor: pointer;
  }
  button.add:hover {
    background-color: #45a049;
  }
</style>
<h2 style="text-align: center;">Добавить категорию</h2>
<form method="post" class="category-add">
  <label>
    <input type="text" name="name" required placeholder="Название категории">
  </label>
  <button type="submit" class="add">Добавить товар</button>
</form>