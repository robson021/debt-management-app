/* tslint:disable:no-unused-variable */
import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {By} from '@angular/platform-browser';
import {DebugElement} from '@angular/core';

import {MyDebtsComponent} from './my-debts.component';

describe('MyDebtsComponent', () => {
  let component: MyDebtsComponent;
  let fixture: ComponentFixture<MyDebtsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MyDebtsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDebtsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
