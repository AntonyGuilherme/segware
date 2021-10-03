import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostesUsuarioComponent } from './postes-usuario.component';

describe('PostesUsuarioComponent', () => {
  let component: PostesUsuarioComponent;
  let fixture: ComponentFixture<PostesUsuarioComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostesUsuarioComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostesUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
