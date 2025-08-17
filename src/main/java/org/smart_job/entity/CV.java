package org.smart_job.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CV extends BaseEntity {
    private Integer id;
    private Integer userId;
    private String filePath;
    private String parsedText;

    // Relationships
//    private User user;
}
