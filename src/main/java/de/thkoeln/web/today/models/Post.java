package de.thkoeln.web.today.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Post {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long id;
  private int sunshineDuration;
  private String title;
  @Column(columnDefinition = "TEXT")
  private String text;
  private String image;
  private LocalDate createdAt;

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public int getSunshineDuration() {
    return sunshineDuration;
  }

  public void setSunshineDuration(int sunshineDuration) {
    this.sunshineDuration = sunshineDuration;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

}
