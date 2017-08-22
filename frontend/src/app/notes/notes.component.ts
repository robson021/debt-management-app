import {Component, OnInit} from '@angular/core';
import {HttpConnectionService} from '../http-connection.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
})
export class NotesComponent implements OnInit {

  noteForm: FormGroup;

  notes = [];

  constructor(private fb: FormBuilder, private http: HttpConnectionService) {
    this.noteForm = fb.group({
      noteText: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.loadNotes();
  }

  loadNotes() {
    this.http.performGet('credentials/my-notes')
      .subscribe(data => {
        this.notes = data;
      });
  }

  submitNewNote() {
    let note = this.noteForm.value.noteText;
    this.http.performPost('credentials/add-note', note)
      .subscribe(data => this.loadNotes());

    this.noteForm.reset();
  }

  deleteNote(noteId) {
    this.http.performDelete('credentials/remove-note/' + noteId + '/')
      .subscribe(data => this.loadNotes());
  }

}
