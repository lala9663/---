package com.example.wantedpreonboardingbackend.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stack")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Stack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stack_id", columnDefinition = "BIGINT")
    private Long stackId;

    @Column(name = "stack_name", columnDefinition = "VARCHAR(10)")
    private String stackName;

}
