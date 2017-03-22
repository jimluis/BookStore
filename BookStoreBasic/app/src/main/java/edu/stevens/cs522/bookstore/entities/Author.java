package edu.stevens.cs522.bookstore.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Author implements Parcelable {
	
	// TODO Modify this to implement the Parcelable interface.

	// NOTE: middleInitial may be NULL!

    public String id;

    public String firstName;
	
	public String middleInitial;
	
	public String lastName;

	public Long bookId;

	public ArrayList<Author> authorsList = new ArrayList<Author>();

	public Author(String aut) {

		String[] authorName = aut.split(", ");

		Author authorInfo = new Author();

		for (int i = 0; i < authorName.length; i++)
		{
			String[] authorSplit = authorName[i].split(" ");

			if(authorSplit.length == 1)
			{
				authorInfo.setFirstName(authorSplit[0]);
			}

			else if(authorSplit.length == 2)
			{
				authorInfo.setFirstName(authorSplit[0]);
				authorInfo.setLastName(authorSplit[1]);
			}

			else if(authorSplit.length == 3)
			{
				authorInfo.setFirstName(authorSplit[0]);
				authorInfo.setMiddleInitial(authorSplit[1]);
				authorInfo.setLastName(authorSplit[2]);
			}

			//authorArray[i] = authorInfo;
			authorsList.add(authorInfo);
			authorInfo = new Author();
		}

		/*String[] name = authorText.split(" ");
		switch (name.length) {
			case 0:
				firstName = lastName = "";
				break;
			case 1:
				firstName = "";
				lastName = name[0];
				break;
			case 2:
				firstName = name[0];
				lastName = name[1];
				break;
			default:
				firstName = name[0];
				middleInitial = name[1];
				lastName = name[2];
		}*/
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (firstName != null && !"".equals(firstName)) {
			sb.append(firstName);
			sb.append(' ');
		}
		if (middleInitial != null && !"".equals(middleInitial)) {
			sb.append(middleInitial);
			sb.append(' ');
		}
		if (lastName != null && !"".equals(lastName)) {
			sb.append(lastName);
		}
		return sb.toString();
	}

	public Author()
	{

	}
	public Author(Parcel parcel){
		firstName = parcel.readString();
		middleInitial = parcel.readString();
		lastName = parcel.readString();
	}

	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(this.firstName);
		parcel.writeString(this.middleInitial);
		parcel.writeString(this.lastName);
	}

	public static final Creator<Author> CREATOR = new Creator<Author>() {
		@Override
		public Author createFromParcel(Parcel parcel) {
			return new Author(parcel);
		}

		@Override
		public Author[] newArray(int i) {

			return new Author[i];
		}
	};

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public ArrayList<Author> getAuthorsList() {
		return authorsList;
	}

	public void setAuthorsList(ArrayList<Author> authorsList) {
		this.authorsList = authorsList;
	}

	public static ArrayList<Author> castingString(String aut)
	{
		String[] authorName = aut.split(", ");
		ArrayList<Author> authorsList2 = new ArrayList<Author>();
		Author authorInfo = new Author();

		for (int i = 0; i < authorName.length; i++)
		{
			String[] authorSplit = authorName[i].split(" ");

			if(authorSplit.length == 1)
			{
				authorInfo.setFirstName(authorSplit[0]);
			}

			else if(authorSplit.length == 2)
			{
				authorInfo.setFirstName(authorSplit[0]);
				authorInfo.setLastName(authorSplit[1]);
			}

			else if(authorSplit.length == 3)
			{
				authorInfo.setFirstName(authorSplit[0]);
				authorInfo.setMiddleInitial(authorSplit[1]);
				authorInfo.setLastName(authorSplit[2]);
			}

			authorsList2.add(authorInfo);
			authorInfo = new Author();
		}
		return authorsList2;
	}

}
