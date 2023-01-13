# TaskManager
I have created Task manager API in spring boot  using mysql database.
It is CRUD API basically create, update, Delete and Fetch board, list & items and also reorder the list and items. 

## Board API

POST /api/v1/boards

![image](https://user-images.githubusercontent.com/55016700/212349573-dfeb6edb-4d85-410a-b4d2-178f251725d8.png)

GET /api/v1/boards

![image](https://user-images.githubusercontent.com/55016700/212350181-65871f77-69d0-4efb-a384-9c810a18f097.png)

UPDATE /api/v1/boards/{board_id}

![image](https://user-images.githubusercontent.com/55016700/212350514-4d0ce683-3abf-48ac-a6aa-7d81ee4c5d0f.png)

# Lists API

POST /api/v1/lists

![image](https://user-images.githubusercontent.com/55016700/212350849-2ca41254-eada-484a-b820-6ca26b841220.png)

GET /api/v1/lists

![image](https://user-images.githubusercontent.com/55016700/212351260-25777d9b-e08c-4175-89ea-e41da4042dfe.png)

UPDATE /api/v1/lists/{list_id}

![image](https://user-images.githubusercontent.com/55016700/212351895-ea5ed541-57cd-4a18-8bb0-181f4003fdd6.png)

REORDER LIST /api/v1/lists/reorder/{list_id}
  
 <li>It reorder the sequence of list. </li>

![image](https://user-images.githubusercontent.com/55016700/212352499-66d5da8d-37b9-4a0c-93bc-fa73b81536e8.png)

# Item API

POST /api/v1/items

![image](https://user-images.githubusercontent.com/55016700/212352941-4e431306-aca0-4267-a47e-6a7b8e2403d3.png)

GET /api/v1/items

![image](https://user-images.githubusercontent.com/55016700/212353175-5b82409e-7b8f-4049-aa5b-c23457bea47c.png)

UPDATE /api/v1/items/{item_id}

![image](https://user-images.githubusercontent.com/55016700/212353498-4853afe3-e7bb-4b8f-979d-078e491e9af4.png)

REORDER ITEM /api/v1/items/reorder/{item_id}

  <li> It reorder the sequence of items. </li>

![image](https://user-images.githubusercontent.com/55016700/212354284-bb720e84-d39d-4e47-aa0d-db5897a1d806.png)

DELETE  /api/v1/items/{item_id}

![image](https://user-images.githubusercontent.com/55016700/212354526-bfdc0d86-0a6e-40e8-9f88-395d9f691951.png)

Final dashboard

![image](https://user-images.githubusercontent.com/55016700/212356018-7a5d6450-662a-44d4-a344-23381c581a07.png)

