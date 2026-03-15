package com.task.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.task.ENUM.IssueStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "board_column",
    indexes = {
        @Index(columnList = "board_id,position")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardColumns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonBackReference
    private Board board;

    private String name;

    private IssueStatus statusKey;

    private Integer position;

    private Integer wiplimit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IssueStatus getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(IssueStatus statusKey) {
		this.statusKey = statusKey;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getWiplimit() {
		return wiplimit;
	}

	public void setWiplimit(Integer wiplimit) {
		this.wiplimit = wiplimit;
	}

}