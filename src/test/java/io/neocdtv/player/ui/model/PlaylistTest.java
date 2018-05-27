package io.neocdtv.player.ui.model;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * PlaylistTest.
 *
 * @author xix
 * @since 29.04.18
 */
public class PlaylistTest {

  public static final int FIRST_INDEX = 0;
  public static final int SECOND_INDEX = 1;
  @InjectMocks
  private Playlist playlist;

  @Spy
  private PlaylistSelection playlistSelection;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test(expected = RuntimeException.class)
  public void empty_playlist() {
    playlist.getCurrentPlaylistEntry();
  }

  @Test
  public void no_current_entry_no_entry_selected_so_choose_the_first_one_as_current() {
    // given
    final PlaylistEntry firstEntry = createListWithTwoElements(FIRST_INDEX);
    // when
    final PlaylistEntry currentPlaylistEntry = playlist.getCurrentPlaylistEntry();
    // then
    assertThat(firstEntry.getUuid(), equalTo(currentPlaylistEntry.getUuid()));
    assertThat(FIRST_INDEX, equalTo(playlistSelection.getMinSelectionIndex()));
  }

  @Test
  public void no_current_entry_and_second_entry_selected_so_choose_the_second_one_as_current() {
    // given--
    final PlaylistEntry firstEntry = createFirstEntry();
    final PlaylistEntry secondEntry = createSecondEntry();
    playlist.addElement(firstEntry);
    playlist.addElement(secondEntry);
    playlistSelection.setSelectionInterval(SECOND_INDEX, SECOND_INDEX);
    // when
    final PlaylistEntry currentPlaylistEntry = playlist.getCurrentPlaylistEntry();
    // then
    assertThat(secondEntry.getUuid(), equalTo(currentPlaylistEntry.getUuid()));
    assertThat(SECOND_INDEX, equalTo(playlistSelection.getMinSelectionIndex()));
  }

  @Test
  public void no_current_entry_and_no_entry_selected_so_choose_the_first_one_as_next(){
    // given
    final PlaylistEntry firstEntry = createListWithTwoElements(FIRST_INDEX);
    // when
    final PlaylistEntry nextPlaylistEntry = playlist.getNextPlaylistEntry();
    // then
    assertThat(firstEntry.getUuid(), equalTo(nextPlaylistEntry.getUuid()));
    assertThat(FIRST_INDEX, equalTo(playlistSelection.getMinSelectionIndex()));
  }

  @Test
  public void first_entry_is_current_and_no_selected_entry_so_choose_the_second_one_as_next(){
    nextPlaylistEntryGeneric(FIRST_INDEX, SECOND_INDEX);
  }

  @Test
  public void second_entry_is_current_and_no_selected_entry_so_choose_the_first_one_as_next(){
    nextPlaylistEntryGeneric(SECOND_INDEX, FIRST_INDEX);

  }

  private void nextPlaylistEntryGeneric(final int currentIndex, final int nextIndex) {
    // given
    final PlaylistEntry secondEntry = createListWithTwoElements(currentIndex);
    playlistSelection.setCurrentPlayingUuid(secondEntry.getUuid());
    // when
    final PlaylistEntry nextPlaylistEntry = playlist.getNextPlaylistEntry();
    // then
    assertThat(playlist.get(nextIndex).getUuid(), equalTo(nextPlaylistEntry.getUuid()));
    assertThat(nextIndex, equalTo(playlistSelection.getMinSelectionIndex()));
  }

  private PlaylistEntry createSecondEntry() {
    return PlaylistEntry.create("bar2000.mp3", "/foo/bar2000.mp3");
  }

  private PlaylistEntry createFirstEntry() {
    return PlaylistEntry.create("bar.mp3", "/foo/bar.mp3");
  }

  private PlaylistEntry createListWithTwoElements(int toReturn) {
    final PlaylistEntry firstEntry = createFirstEntry();
    final PlaylistEntry secondEntry = createSecondEntry();
    playlist.addElement(firstEntry);
    playlist.addElement(secondEntry);
    return playlist.get(toReturn);
  }
}