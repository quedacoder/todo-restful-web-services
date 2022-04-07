package com.quedacoder.todorestfulwebservices.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.quedacoder.todorestfulwebservices.model.Todo;
import com.quedacoder.todorestfulwebservices.service.TodoHardcodedService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
public class TodoResourse {
	
	@Autowired
	private TodoHardcodedService todoService;
	
	@GetMapping("/users/{username}/todos")
	public List<Todo> getAllTodos(@PathVariable String username) {
		
		return todoService.findAll();
	}
	
	@DeleteMapping("/users/{username}/todos/{todo_id}")
	public ResponseEntity<Void> deleteTodoItem(@PathVariable String username, @PathVariable long todo_id) {
		
		Todo todo =  todoService.deleteById(todo_id);
		
		if (todo != null) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@GetMapping("/users/{username}/todo/{id}")
	public Todo getTodo(@PathVariable String username, @PathVariable long id) {
		
		return todoService.findById(id);
	}
	
	@PutMapping("/users/{username}/todo/{id}")
	public ResponseEntity<Todo> updateTodoItem(@PathVariable String username, @PathVariable long id, @RequestBody Todo todo) {
		
		Todo updatedTodo = todoService.save(todo);
		return new ResponseEntity<Todo>(updatedTodo, HttpStatus.OK);
		
	}
	
	@PostMapping("/users/{username}/todo")
	public ResponseEntity<Void> createTodoItem(@PathVariable String username, @RequestBody Todo todo) {
		
		Todo createdTodo = todoService.save(todo);
		
		//Get current resource url
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}

}
