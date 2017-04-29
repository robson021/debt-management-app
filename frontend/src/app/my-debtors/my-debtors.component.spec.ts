/* tslint:disable:no-unused-variable */
import {async, ComponentFixture, TestBed} from '@angular/core/testing';
import {By} from '@angular/platform-browser';
import {DebugElement} from '@angular/core';

import {MyDebtorsComponent} from './my-debtors.component';

describe('MyDebtorsComponent', () => {
  let component: MyDebtorsComponent;
  let fixture: ComponentFixture<MyDebtorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MyDebtorsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyDebtorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
