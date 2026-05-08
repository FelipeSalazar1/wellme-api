package com.fiap.wellme.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trails")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TrailCategory category;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "order_index", nullable = false)
    @Builder.Default
    private Integer orderIndex = 0;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}
