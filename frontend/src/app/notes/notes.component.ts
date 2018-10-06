import {Component, OnInit} from '@angular/core';
import {HttpConnectionService} from '../http-connection.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
})
export class NotesComponent implements OnInit {

  private NOTES_URI = 'notes/';

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

    this.http.performGet(this.NOTES_URI)
      .subscribe(data => {
        this.notes = data;
      });
  }

  submitNewNote() {
    let note = this.noteForm.value.noteText;
    this.http.performPost(this.NOTES_URI, note)
      .subscribe(data => this.loadNotes());

    this.noteForm.reset();
  }

  deleteNote(noteId) {
    this.http.performDelete(this.NOTES_URI + noteId + '/')
      .subscribe(data => this.loadNotes());
  }

}
